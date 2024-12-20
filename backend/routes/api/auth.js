import express from "express";
import { actions } from "../../controllers/auth.js";
import { validateBody, authenticate } from "../../middlewares/index.js";
import { schemas } from "../../models/user.js";

const router = express.Router();

router.post("/register", validateBody(schemas.registerSchema), actions.register);
router.post("/login", validateBody(schemas.loginSchema), actions.login);
router.post("/logout", actions.logout, authenticate); 
router.get("/data", actions.getUserData, authenticate); 
router.put("/data", actions.updateUserData, authenticate); 
// router.get("/current", authenticate, actions.current); 
// router.post("/password_recovery", ctrl.passwordRecovery);

// verify with token

export default router;