# AI Context

## Project
**London Land Value Simulator (MVP)** — a learning-focused engineering project to explore transparent land valuation and illustrative Land Value Tax (LVT) scenarios for London.

## Contributors
- **Dev** – primary owner, engine module, domain modelling
- **Sam** – backend, Spring Boot API, data ingestion

## Current state
- Multi-module Maven project (`engine`, `backend`)
- Java 21
- All builds and tests pass from repo root
- SSH + GitHub configured

### Engine module
- ValuationCalculator + ValuationResult
- PostcodeNormalizer + PostcodeValidator
- Full unit test coverage
- No framework dependencies

### Backend module
- Spring Boot REST API
- `/valuation` endpoint
- MockMvc controller tests
- Depends on engine module only

---

## Current API
Returns:
- normalizedPostcode
- medianPrice
- landShare
- taxRate
- landValuePerDwelling
- annualLandTax

---

## Near-term goal
Remove the `medianPrice` query parameter and replace it with:
- postcode → zone lookup
- zone → median sale price derived from data

---

## Planned data sources
- HM Land Registry Price Paid Data
- ONS Postcode Directory (postcode → geography)

---

## Engineering principles
- Engine is pure Java (no frameworks)
- Backend is thin orchestration only
- Clear validation boundaries
- Deterministic, testable logic
- No hidden assumptions

---

## Next tasks
1. Introduce zone abstraction (postcode prefix / LSOA)
2. Stub median values by zone
3. Load real datasets
4. Add minimal frontend
