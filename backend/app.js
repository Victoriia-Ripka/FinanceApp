import express from "express";
import logger from "morgan";
import cors from "cors";
import authRouter from "./routes/api/auth.js";
import currencyRoutes from "./routes/api/currency.js";
import groupRoutes from "./routes/api/group.js";
import financeRoutes from "./routes/api/finance.js";

const app = express();

const formatsLogger = app.get("env") === "development" ? "dev" : "short";

app.use(logger(formatsLogger));
app.use(cors());
app.use(express.json())
app.use(express.static("public"));

app.use("/api/users", authRouter);
app.use("/api/currency", currencyRoutes);
app.use("/api/group", groupRoutes);
app.use("/api/finance", financeRoutes);

app.use((_, res, __) => {
  res.status(404).json({
    status: "error",
    code: 404,
    message: "Use api on routes: /api/contacts",
    data: "Not found",
  });
});

app.use((err, _, res, __) => {
  console.log(err.stack);
  res.status(500).json({
    status: "fail",
    code: 500,
    message: err.message,
    data: "Internal Server Error",
  });
});

export default app;