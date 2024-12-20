import dotenv from "dotenv";
import { User } from "../models/user.js";
import ctrlWrapper from "../helpers/CtrlWrapper.js";
import { nanoid } from "nanoid";

dotenv.config();

const getGroupUsers = async (req, res) => {
  const token = req.headers.authorization?.split(" ")[1];
  const user = await User.findOne({ token });
  if (!user) {
    res.status(404).json({ message: "User not found" });
    return;
  }
    
  const users = await User.find({ referalCode: user.referalCode }).select({ name: 1, email: 1, role: 1 });
  res.status(200).json({ "referalCode": user.referalCode, "currency": user.currency, users });
}

const deleteUserFromGroup = async (req, res) => {
    const { userId } = req.query;
    const token = req.headers.authorization?.split(" ")[1];

    const user = await User.findOne({ token });
    if (!user) {
      res.status(404).json({ message: "User not found" });
      return;
    }

    const userToDelete = await User.findById(userId);
    if (userToDelete.role === 'admin') {
      res.status(400).json({ message: "Can't delete the admin of the group" });
      return;
    }

    const referalCode = nanoid(6);
    await User.findByIdAndUpdate(userId, { referalCode, role: 'admin' }, { new: true } );
    res.status(204).end();
}


export const actions = {
  getGroupUsers: ctrlWrapper(getGroupUsers),
  deleteUserFromGroup: ctrlWrapper(deleteUserFromGroup),
};