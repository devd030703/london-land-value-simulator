# London Land Value Simulator (MVP)

A learning project to build a simple, transparent tool that estimates **indicative land value** and an **illustrative Land Value Tax (LVT)** for London addresses/postcodes — starting with **Tower Hamlets**.

## What this is
- A **scenario simulator**, not a legal or billing tool
- Focused on clarity, transparency, and iteration
- Built to help us learn real engineering: data pipelines, APIs, testing, and clean domain modelling

## V1 Scope (Tower Hamlets)
### Inputs
- Postcode (e.g. `E14 8HX`)
- Land share % (e.g. 10%–80%)
- LVT rate % (e.g. 0%–10%)

### Outputs
- Borough + zone (e.g. LSOA/ward)
- Typical local property value (median of recent sales in the zone)
- **Estimated land value per dwelling**
- **Estimated annual land tax per dwelling**
- Assumptions & limitations (always visible)

### Non-goals (V1)
- Exact legal tax liability / exact bills
- Exact per-flat land allocation (leasehold complexity)
- All London boroughs
- Complex ML valuation models

## Definitions
- **Dwelling**: one self-contained home (flat/house/maisonette), regardless of tenure.
- **Leasehold flat snag**: flats do not have distinct plots of land. V1 reports **indicative land value per dwelling**, not per-plot land area.

## Valuation model (V1)
### Steps
1. Normalise & validate postcode
2. Lookup postcode → borough + zone
3. For the zone, compute **median sale price** from recent transactions
4. Apply land share:
   - `land_value_per_dwelling = median_price × land_share`
5. Apply tax rate:
   - `annual_lvt = land_value_per_dwelling × tax_rate`

### Notes
- Use `BigDecimal` for money and rates
- Rates are decimals in code (e.g. `0.40 = 40%`)

## Data sources (V1)
- **HM Land Registry Price Paid Data** (sold prices)
- **ONS Postcode Directory (ONSPD)** (postcode → geographies)

V1 filters transactions to:
- Borough: Tower Hamlets
- Time window: last **5 years** (configurable)

## Planned API
- `GET /postcode/{postcode}` → normalised postcode + borough + zone
- `GET /valuation?postcode=E14+8HX&landShare=0.4&taxRate=0.03` → valuation response

## Repo structure
- `engine/` — Pure Java valuation library (calculator + postcode utilities + unit tests)
- `backend/` — Spring Boot API (data access + endpoints)
- `frontend/` — Web UI
- `data/` — scripts/notes for downloading + processing datasets
- `docs/` — project docs

## Milestones
### Milestone 1: Working engine (no UI)
- Postcode normaliser + validator
- Valuation calculator
- Unit tests

### Milestone 2: Working API
- Data loaded (Tower Hamlets)
- Endpoints return real data

### Milestone 3: MVP UI
- Postcode input + sliders + results on a simple page

## Assumptions & limitations (user-facing copy)
This tool provides an **indicative estimate** designed to explore scenarios.
- Results are **per dwelling**, not per plot, because flats do not have distinct land parcels.
- Typical values are derived from **median recent sales** in the local zone.
- Outputs should not be treated as legal or financial advice.

## Contributing
Use feature branches and open PRs for review.

Suggested branch names:
- `feature/<short-name>`
- `fix/<short-name>`
