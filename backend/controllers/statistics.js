import dotenv from "dotenv";
import { Record } from "../models/record.js";
import { Balance } from "../models/balance.js";
import { Category } from "../models/category.js";
import ctrlWrapper from "../helpers/CtrlWrapper.js";
import { getBalanceId } from "./finance.js";

dotenv.config();

const getStatisticsForMonth = async (req, res) => {
  const balanceId = await getBalanceId(req.user.token);
  const balance = await Balance.findById(balanceId).select("currency");

  const { month, year } = req.query;
  let startOfMonth, endOfMonth;
  let resolvedMonth, resolvedYear;

  if (month && year) {
    startOfMonth = new Date(year, month - 1, 1);
    endOfMonth = new Date(year, month, 0);
    resolvedMonth = parseInt(month, 10);
    resolvedYear = parseInt(year, 10);
  } else {
    const now = new Date();
    startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
    endOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0);
    resolvedMonth = now.getMonth() + 1;
    resolvedYear = now.getFullYear();
  }

  const [typeTotal] = await Promise.all([
    Record.aggregate([
      {
        $match: {
          balanceId: balanceId,
          type: req.query.type,
          date: { $gte: startOfMonth, $lte: endOfMonth },
        },
      },
      {
        $group: {
          _id: null,
          total: { $sum: "$value" },
        },
      },
    ])
  ]);

  const total = typeTotal.length > 0 ? typeTotal[0].total : 0;

  const userCategories = await Category.find({ balanceId }).select({ title: 1, _id: 1 });
  const categoriesRecords = await Promise.all(
    userCategories.map(async (category) => ({
      title: category.title,
      total: await Record.aggregate([
        {
          $match: {
            balanceId,
            type: req.query.type,
            categoryId: category._id,
            date: { $gte: startOfMonth, $lte: endOfMonth },
          },
        },
        {
          $group: {
            _id: null,
            total: { $sum: "$value" },
          },
        },
      ]).then((result) => result.length > 0 ? result[0].total : 0),
      categoryId: category._id
    }))
  );

  const filteredCategoriesRecords = categoriesRecords
    .filter((record) => record.total > 0)
    .map((record) => {
      const total = categoriesRecords.reduce((sum, r) => sum + r.total, 0);
      return {
        ...record,
        percentage: parseFloat(((record.total / total) * 100).toFixed(2)),
      };
    });

  const [cash, card] = await Promise.all([
    Record.aggregate([
      {
        $match: {
          balanceId: balanceId,
          type: req.query.type,
          method: "cash",
          date: { $gte: startOfMonth, $lte: endOfMonth },
        },
      },
      {
        $group: {
          _id: null,
          total: { $sum: "$value" },
        },
      },
    ]),
    Record.aggregate([
      {
        $match: {
          balanceId: balanceId,
          type: req.query.type,
          method: "card",
          date: { $gte: startOfMonth, $lte: endOfMonth },
        },
      },
      {
        $group: {
          _id: null,
          total: { $sum: "$value" },
        },
      },
    ]),
  ]);

  const cashPercentage = cash[0]?.total ? parseFloat(((cash[0].total / total) * 100).toFixed(2)) : 0;
  const cardPercentage = card[0]?.total ? parseFloat(((card[0].total / total) * 100).toFixed(2)) : 0;

  res.status(200).json({
    currency: balance.currency,
    resolvedMonth,
    resolvedYear,
    total,
    categories: filteredCategoriesRecords,
    cashPercentage,
    cardPercentage,
  });
}

const getStatisticsForYear = async (req, res) => {
  const balanceId = await getBalanceId(req.user.token);
  const balance = await Balance.findById(balanceId).select("currency");

  const { year } = req.query;
  let startOfYear, endOfYear;
  let resolvedYear;

  if (year) {
    startOfYear = new Date(year, 0, 1); 
    endOfYear = new Date(year, 11, 31); 
    resolvedYear = parseInt(year, 10);
  } else {
    const now = new Date();
    startOfYear = new Date(now.getFullYear(), 0, 1);
    endOfYear = new Date(now.getFullYear(), 11, 31);
    resolvedYear = now.getFullYear();
  }

  const [typeTotal] = await Promise.all([
    Record.aggregate([
      {
        $match: {
          balanceId: balanceId,
          type: req.query.type,
          date: { $gte: startOfYear, $lte: endOfYear },
        },
      },
      {
        $group: {
          _id: null,
          total: { $sum: "$value" },
        },
      },
    ]),
  ]);

  const total = typeTotal.length > 0 ? typeTotal[0].total : 0;

  const userCategories = await Category.find({ balanceId }).select({ title: 1, _id: 1 });
  const categoriesRecords = await Promise.all(
    userCategories.map(async (category) => ({
      title: category.title,
      total: await Record.aggregate([
        {
          $match: {
            balanceId,
            type: req.query.type,
            categoryId: category._id,
            date: { $gte: startOfYear, $lte: endOfYear },
          },
        },
        {
          $group: {
            _id: null,
            total: { $sum: "$value" },
          },
        },
      ]).then((result) => (result.length > 0 ? result[0].total : 0)),
      categoryId: category._id,
    }))
  );

  const filteredCategoriesRecords = categoriesRecords
    .filter((record) => record.total > 0)
    .map((record) => {
      const total = categoriesRecords.reduce((sum, r) => sum + r.total, 0);
      return {
        ...record,
        percentage: parseFloat(((record.total / total) * 100).toFixed(2)),
      };
    });

  const [cash, card] = await Promise.all([
    Record.aggregate([
      {
        $match: {
          balanceId: balanceId,
          type: req.query.type,
          method: "cash",
          date: { $gte: startOfYear, $lte: endOfYear },
        },
      },
      {
        $group: {
          _id: null,
          total: { $sum: "$value" },
        },
      },
    ]),
    Record.aggregate([
      {
        $match: {
          balanceId: balanceId,
          type: req.query.type,
          method: "card",
          date: { $gte: startOfYear, $lte: endOfYear },
        },
      },
      {
        $group: {
          _id: null,
          total: { $sum: "$value" },
        },
      },
    ]),
  ]);

  const cashPercentage = cash[0]?.total ? parseFloat(((cash[0].total / total) * 100).toFixed(2)) : 0;
  const cardPercentage = card[0]?.total ? parseFloat(((card[0].total / total) * 100).toFixed(2)) : 0;

  res.status(200).json({
    currency: balance.currency,
    resolvedYear,
    total,
    categories: filteredCategoriesRecords,
    cashPercentage,
    cardPercentage,
  });
};



export const actions = {
  getStatisticsForMonth: ctrlWrapper(getStatisticsForMonth),
  getStatisticsForYear: ctrlWrapper(getStatisticsForYear),
};