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
import { Record } from "../models/record.js";

dotenv.config();

const addCategory = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const balanceId = await getBalanceId(token);
  await Category.create({title: req.body.title, balanceId: balanceId._id  });
  res.status(201).json({ message: "Category added successfully" });
}

const getCategories = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const balanceId = await getBalanceId(token);
  const categories = await Category.find({balanceId: balanceId._id  });
  res.status(200).json({ categories });
}

const deleteCategory = async (req, res) => {
  await Category.findByIdAndDelete({ _id: req.query.id }); 
  res.status(204).json({ message: "Category deleted successfully" });
}

const addRecord = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const balanceId = await getBalanceId(token);
  await Record.create({...req.body, balanceId: balanceId._id  });
  res.status(201).json({ message: "Record added successfully" });
}

const getRecords = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const balanceId = await getBalanceId(token);
  const records = await Record.find({balanceId: balanceId._id  });
  res.status(200).json({ records });
}

const deleteRecord = async (req, res) => {
  await Record.findByIdAndDelete({ _id: req.query.id }); 
  res.status(204).json({ message: "Record deleted successfully" });
}

const getBalanceId = async (token) => {
  const userCode = await User.findOne({ token }).select({ referalCode: 1 });
  const groupId = await Group.findOne({ referalCode: userCode.referalCode }).select({ _id: 1 });
  const balanceId = await Balance.findOne({ groupId: groupId._id }).select({ _id: 1 });
  return balanceId._id;
}

export const actions = {
  addCategory: ctrlWrapper(addCategory),
  getCategories: ctrlWrapper(getCategories),
  deleteCategory: ctrlWrapper(deleteCategory),
  addRecord: ctrlWrapper(addRecord),
  getRecords: ctrlWrapper(getRecords),
  deleteRecord: ctrlWrapper(deleteRecord)
};