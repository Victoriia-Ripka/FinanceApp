при локальному запуску спочатку *npm i*
run the project *npm run start*



## API Endpoints `http://localhost:8080` 

| Method | URL | Params | Body | Response |
|--------|-----|--------|------|----------|
| **POST** | /api/users/register/ | - | {name, email, password, currency, referalCode*} | JSON object with currency data |
| **POST** | /api/users/login/ | - |  {email, password} | JSON object with currency rates |
| **POST** | /api/users/logout/ | - |  {email} | JSON object with main currency rates |
|--------|-----|--------|------|----------|
| **GET** | /api/currency | - | - | JSON object with currency data |
| **GET** | /api/currency/rates | - | - | JSON object with currency rates |
| **GET** | /api/currency/rates/main | - | - | JSON object with main currency rates |

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