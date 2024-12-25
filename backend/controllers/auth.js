import { nanoid } from "nanoid";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import dotenv from "dotenv";
import { User } from "../models/user.js";
import { Group } from "../models/group.js";
import { Balance } from "../models/balance.js";
import { Category } from "../models/category.js";
import { Record } from "../models/record.js";
import HttpError from "../helpers/HttpError.js";
import ctrlWrapper from "../helpers/CtrlWrapper.js";
import { getBalanceId } from "./finance.js";

dotenv.config();

const { SECRET_KEY } = process.env;

const register = async (req, res) => {
  const { email, password } = req.body;
  const user = await User.findOne({ email });

  if (user) {
    res.status(409);
    throw HttpError(409, "Email in use");
  }

  const hashPassword = await bcrypt.hash(password, 10);
  const payload = {
    email
  };
  const token = jwt.sign(payload, SECRET_KEY, { expiresIn: "72h" });

  let newUser;

  if (req.body.referalCode) {
    const groupAdminUser = await User.findOne({ referalCode: req.body.referalCode, role: "admin" });
    newUser = await User.create({
      ...req.body,
      id: nanoid(),
      password: hashPassword,
      referalCode: req.body.referalCode,
      currency: groupAdminUser.currency,
      role: "user",
      token
    });
  } else { // without referalCode
    const referalCode = nanoid(6);
    newUser = await User.create({
      ...req.body,
      id: nanoid(),
      password: hashPassword,
      referalCode,
      role: "admin",
      token
    });
    const newGroup = await Group.create({ adminId: newUser._id, referalCode });
    const balance = await Balance.create({ groupId: newGroup._id, currency: newUser.currency });
    await Category.create({ title: 'other', balanceId: balance._id });
  }

  res.status(201).json({
    token: newUser.token
  });
};

const login = async (req, res) => {
  const { email, password } = req.body;
  const user = await User.findOne({ email });
  if (!user) {
    throw HttpError(404);
  }

  const passwordCompare = await bcrypt.compare(password, user.password);
  if (!user || !passwordCompare) {
    throw HttpError(401, "Email or password is wrong");
  }

  const payload = {
    email
  };

  const token = jwt.sign(payload, SECRET_KEY, { expiresIn: "72h" });
  const updatedUser = await User.findByIdAndUpdate(user._id, { token }, { new: true });

  res.status(200).json({
    token: updatedUser.token
  });
};

// TODO: current

const logout = async (req, res) => {
  const user = await User.findOneAndUpdate({ token: req.user.token }, { token: "" }, { new: true });
  if (!user) {
    throw HttpError(404)
  }
  res.status(204).json({ message: "Logged out successfully" });
};

const passwordRecovery = async (req, res) => {
  const { email, name, currency } = req.body;
  const user = await User.findOne({ email });

  if (!user || user.name!== name || user.currency!== currency) {
    res.status(404).json({ message: "Data doesn't match" });
    return;
  }

  const payload = {
    email
  };

  const token = jwt.sign(payload, SECRET_KEY, { expiresIn: "72h" });

  const newPassword = nanoid(6);
  const updatedUSer = await User.findByIdAndUpdate(user._id, { password: await bcrypt.hash(newPassword, 10), token }, { new: true });

  res.status(200).json({ token: updatedUSer.token });
};

const deleteUser = async (req, res) => {
  const user = await User.findOne({ token: req.user.token });
  if (!user) {
    res.status(404).json({ message: "User not found" });
    return;
  }
  if (user.role === 'admin') {
    await Group.findByIdAndDelete(user.referalCode);
    const balanceId = getBalanceId(user.token);
    await Balance.findByIdAndDelete(balanceId);
    await Category.deleteMany({ balanceId: balanceId });
    await Record.deleteMany({ balanceId: balanceId });
    const users = await User.find({ role: "user", referalCode: user.referalCode });
    await Promise.all(users.map(async (u) => await User.findByIdAndDelete(u._id)));
  } else {
    await User.findByIdAndDelete(user._id);
  } 

  res.status(204).json({ message: "User deleted successfully" });
}

const getUserData = async (req, res) => {
  const user = await User.findOne({ token: req.user.token });
  if (!user) {
    res.status(404).json({ message: "User not found" });
    return;
  }

  res.status(200).json({
    user: {
      name: user.name,
      email: user.email,
      currency: user.currency,
      role: user.role,
      referalCode: user.referalCode
    }
  });
};

const updateUserData = async (req, res) => {
  const user = await User.findOne({ token: req.user.token });

  const updatedUser = await User.findByIdAndUpdate(user._id, req.body, { new: true });

  if (req.body.currency && req.body.currency !== user.currency) {
    const balanceId = await getBalanceId(req.user.token);
    await Balance.findByIdAndUpdate(balanceId, { currency: updatedUser.currency }, { new: true });
  }

  res.status(200).json({
    user: {
      name: updatedUser.name,
      email: updatedUser.email,
      currency: updatedUser.currency,
      role: updatedUser.role,
      referalCode: updatedUser.referalCode
    }
  });
};

export const actions = {
  register: ctrlWrapper(register),
  login: ctrlWrapper(login),
  logout: ctrlWrapper(logout),
  getUserData: ctrlWrapper(getUserData),
  updateUserData: ctrlWrapper(updateUserData),
  deleteUser: ctrlWrapper(deleteUser),
  passwordRecovery: ctrlWrapper(passwordRecovery),
};