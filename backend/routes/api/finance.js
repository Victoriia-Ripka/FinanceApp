import express from "express";
import { actions } from "../../controllers/finance.js";
import { validateBody, authenticate } from "../../middlewares/index.js";
import { categorySchemas } from "../../models/category.js";
import { recordSchemas } from "../../models/record.js";

const router = express.Router();

router.post("/categories", validateBody(categorySchemas.newCategorySchema), actions.addCategory, authenticate);
router.get("/categories/all", actions.getCategories, authenticate);
router.delete("/categories", actions.deleteCategory, authenticate);

router.post("/records", validateBody(recordSchemas.newRecordSchema), actions.addRecord, authenticate);
router.get("/records/all", actions.getRecords, authenticate);
router.delete("/records", actions.deleteCategory, authenticate);

export default router;