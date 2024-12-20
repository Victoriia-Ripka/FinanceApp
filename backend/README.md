при локальному запуску спочатку *npm i*
run the project *npm run start*



## API Endpoints `https://financeappbackend-t7l2.onrender.com/`  (`http://localhost:8080` )

| Method | URL | Params | Body | Response | token |
|--------|-----|--------|------|----------|------|
| **POST** | /api/users/register/ | - | {name, email, password, currency, referalCode*} | {token} | no |
| **POST** | /api/users/login/ | - |  {email, password} | {token} | no |
| **POST** | /api/users/logout/ | - |  {email} | - | yes |
| **GET** | /api/users/data/ | - |  {token} | {user : {name, email, currency, referalCode, role}} | yes |
| **PUT** | /api/users/data/ |  |  {token, name, currency, role} | {user : {name, email, currency, referalCode, role}} | yes |
|--------|-----|--------|------|----------|------|
| **GET** | /api/currency | - | - | JSON object with currency data | no |
| **GET** | /api/currency/rates | - | - | JSON object with currency rates | no |
| **GET** | /api/currency/rates/main | - | - | JSON object with main currency rates | no |

### Examples:
#### 1. Get Currency
- **URL**: `/api/currency`
- **Method**: `GET`
- **Response**:
  - `200 OK`: JSON object containing all available currencies.

#### 2. Get Currency Rates
- **URL**: `/api/currency/rates`
- **Method**: `GET`
- **Response**:
  - `200 OK`: JSON object with all currency rates.

#### 3. Get Main Currency Rates
- **URL**: `/api/currency/rates/main`
- **Method**: `GET`
- **Response**:
  - `200 OK`: JSON object with the main currency rates.