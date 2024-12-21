import express from "express";
import { actions } from "../../controllers/finance.js";
import { validateBody, authenticate } from "../../middlewares/index.js";
import { schemas } from "../../models/category.js";

const router = express.Router();

router.post("/categories", validateBody(schemas.newCategorySchema), actions.addCategory, authenticate);
router.get("/categories/all", validateBody(schemas.newCategorySchema), actions.getCategories, authenticate);
router.delete("/categories", validateBody(schemas.newCategorySchema), actions.deleteCategory, authenticate);

export default router;