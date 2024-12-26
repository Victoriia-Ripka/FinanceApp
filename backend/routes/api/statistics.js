import express from "express";
import { authenticate } from "../../middlewares/index.js";
import { actions } from "../../controllers/statistics.js";

const router = express.Router();

router.get("/month", authenticate, actions.getStatisticsForMonth); // ?type="income/expense"&month=MM&year=YYYY
router.get("/year", authenticate, actions.getStatisticsForYear); // ?type="income/expense"&year=YYYY

export default router;