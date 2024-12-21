import { nanoid } from "nanoid";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import dotenv from "dotenv";
import { User } from "../models/user.js";
import { Group } from "../models/group.js";
import { Balance } from "../models/balance.js";
import { Category } from "../models/category.js";
import HttpError from "../helpers/HttpError.js";
import ctrlWrapper from "../helpers/CtrlWrapper.js";

dotenv.config();

const addCategory = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const userCode = await User.findOne({ token }).select({ referalCode: 1 });
  const groupId = await Group.findOne({ referalCode: userCode.referalCode }).select({ _id: 1 });
  const balanceId = await Balance.findOne({ groupId: groupId._id }).select({ _id: 1 });
  await Category.create({title: req.body.title, balanceId: balanceId._id  });
  res.status(201).json({ message: "Category added successfully" });
}

const getCategories = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const userCode = await User.findOne({ token }).select({ referalCode: 1 });
  const groupId = await Group.findOne({ referalCode: userCode.referalCode }).select({ _id: 1 });
  const balanceId = await Balance.findOne({ groupId: groupId._id }).select({ _id: 1 });
  const categories = await Category.find({balanceId: balanceId._id  });
  res.status(200).json({ categories });
}

const deleteCategory = async (req, res) => {
  await Category.findByIdAndDelete({ _id: req.query.id }); 
  res.status(204).json({ message: "Category deleted successfully" });
}

export const actions = {
  addCategory: ctrlWrapper(addCategory),
  getCategories: ctrlWrapper(getCategories),
  deleteCategory: ctrlWrapper(deleteCategory)
};