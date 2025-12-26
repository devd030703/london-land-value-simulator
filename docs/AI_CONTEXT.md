# AI Context

## Project
**London Land Value Simulator (MVP)** — a learning project to explore simple, transparent land valuation assumptions and an illustrative Land Value Tax (LVT) for London, starting with **Tower Hamlets**.

## Current status
- Local repo created on macOS Desktop
- Git initialised and pushed successfully to GitHub
- SSH auth working
- Repo folders exist: `engine/`, `backend/`, `frontend/`, `data/`, `docs/`

## V1 Goal
Given a **postcode** (Tower Hamlets), show:
- Borough + zone
- Typical local property value (median of recent sales in the zone)
- **Estimated land value per dwelling** (scenario-based)
- **Estimated annual land tax per dwelling** (scenario-based)
- Clear assumptions/limitations

## V1 Model
1. Normalise & validate postcode
2. Lookup postcode → borough + zone
3. Compute `median_price(zone)` from recent transactions
4. Apply land share:
   - `land_value_per_dwelling = median_price × land_share`
5. Apply tax rate:
   - `annual_lvt = land_value_per_dwelling × tax_rate`

Notes:
- Use `BigDecimal` for money and rates
- Rates stored as decimals (e.g. `0.40` for 40%)

## V1 Data sources (planned)
- HM Land Registry Price Paid Data (sold prices)
- ONS Postcode Directory (postcode → geographies)

## Responsibilities
- Dev owns `engine/` module: pure Java valuation logic + postcode utilities + unit tests
- Friend owns `backend/` module: Spring Boot API + data pipeline
- Frontend is minimal in V1 (postcode + sliders + results)

## Next tasks
- Create Maven multi-module structure (parent `pom.xml`, `engine` module, tests)
- Implement `PostcodeNormalizer`, `PostcodeValidator`, `ValuationCalculator` + JUnit tests
