import express from "express";
import { authenticate } from "../../middlewares/index.js";
import { actions } from "../../controllers/group.js";

const router = express.Router();

router.get("/users", actions.getGroupUsers, authenticate);
router.delete("/users", actions.deleteUserFromGroup, authenticate);

export default router;