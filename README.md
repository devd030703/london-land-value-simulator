# London Land Value Simulator (MVP)

A learning project to build a **simple, transparent land valuation and Land Value Tax (LVT) simulator** for London, starting with **Tower Hamlets**.

The goal is to explore how land value (separate from buildings) might be approximated using public data and clear assumptions — not to produce legally binding valuations.

---

## What this is
- A **scenario simulator**, not a tax or billing system
- Focused on clarity, transparency, and iteration
- Built as a real multi-module Java project to practice clean engineering

## What this is not
- A legally accurate valuation model
- A precise per-flat land allocation tool (leasehold complexity is out of scope)
- A complete London-wide system (yet)

---

## Current capabilities (December 2025)

### Backend API
A working Spring Boot backend exposes a valuation endpoint.

### Query parameters
- `postcode` (e.g. `E14 8HX`)
- `medianPrice` (temporary, numeric)
- `landShare` (0–1, e.g. `0.4`)
- `taxRate` (0–1, e.g. `0.03`)

### Behaviour
- Normalises and validates UK postcodes
- Calculates indicative land value per dwelling
- Calculates illustrative annual land tax
- Returns JSON

This endpoint is fully tested using MockMvc.

---

## Valuation model (V1)

1. Normalise & validate postcode
2. Determine typical local property value (currently passed in; will be data-driven)
3. Apply land share
4. Apply tax rate

All calculations use `BigDecimal` with consistent rounding.

---

## Repo structure
- `engine/` – Pure Java valuation + postcode logic (no Spring)
- `backend/` – Spring Boot REST API (depends on engine)
- `frontend/` – (planned)
- `data/` – (planned: Land Registry + ONS datasets)
- `docs/` – Project documentation

---

## Contributors
- **Dev** – engine module, core valuation logic, project direction
- **Sam** – backend, API wiring, data pipeline (in progress)

---

## Roadmap (next milestones)

1. Replace `medianPrice` parameter with zone-based median price lookup
2. Introduce stub dataset (postcode prefix → median value)
3. Load real Land Registry Price Paid data
4. Add minimal frontend (postcode + sliders)
5. Extend to additional London boroughs

---

## Assumptions & limitations
- Values are **per dwelling**, not per land plot
- Flats do not have discrete land parcels
- Outputs are indicative only
