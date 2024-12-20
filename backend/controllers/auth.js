import { nanoid } from "nanoid";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import dotenv from "dotenv";
import { User } from "../models/user.js";
import { Group } from "../models/group.js";
import { Balance } from "../models/balance.js";
import HttpError from "../helpers/HttpError.js";
import ctrlWrapper from "../helpers/CtrlWrapper.js";

dotenv.config();

const { SECRET_KEY, BASE_URL } = process.env;

const register = async (req, res) => {
  const { email, password } = req.body;
  const user = await User.findOne({ email });

  if (user) {
    res.status(409);
    throw HttpError(409, "Email in use");
  }
    
  // without referalCode
  const hashPassword = await bcrypt.hash(password, 10);
  const referalCode = nanoid(6);
  const payload = {
    email
  };
  const token = jwt.sign(payload, SECRET_KEY, { expiresIn: "72h" });

  const newUser = await User.create({
    ...req.body,
    id: nanoid(),
    password: hashPassword,
    referalCode,
    role: "admin",
    token
  });

  const newGroup = await Group.create({ adminId: newUser._id, referalCode });

  await Balance.create({ groupId: newGroup._id, currency: newUser.currency, total: 0 });

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

const logout = async (req, res) => {
  const { email } = req.body; 
  const user = await User.findOneAndUpdate({ email }, { token: "" });
  if (!user) { 
    HttpError(404)
  }
  res.status(204).end();
};

const getUserData = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const user = await User.findOne({ token });
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
  const token = req.headers.authorization?.split(" ")[1];
  const user = await User.findOne({ token });

  const updatedUser = await User.findByIdAndUpdate(user._id, req.body, { new: true } );

  res.status(200).json({
    user: {
      name: updatedUser.name,
      email: updatedUser.email,
      currency: updatedUser.currency,
      role: updatedUser.role,
      referalCode: updatedUser.referalCode
  } });
};

// TODO: passwordRecovery
// TODO: current

export const actions = {
  register: ctrlWrapper(register),
  login: ctrlWrapper(login),
  logout: ctrlWrapper(logout),
  getUserData: ctrlWrapper(getUserData),
  updateUserData: ctrlWrapper(updateUserData)
};