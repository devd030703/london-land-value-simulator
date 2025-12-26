London Land Value Simulator (MVP)
Purpose
Build a simple, transparent tool that estimates indicative land value and an illustrative Land Value Tax (LVT) for a London address/postcode, starting with Tower Hamlets.
This is a learning project: ship something real, keep assumptions explicit, and iterate.
Scope
V1 (Tower Hamlets only)
User enters: postcode (e.g., E14 8HX)
User adjusts:
Land share % (e.g., 10%–80%)


LVT rate % (e.g., 0%–10%)


User sees:
Borough + zone (e.g., LSOA/ward)


Typical local property value (median of recent sales)


Estimated land value per dwelling


Estimated annual land tax per dwelling


Assumptions / limitations


Non-goals (V1)
Exact legal tax liability or billing


Exact per-flat land allocation (leasehold complexity)


All London boroughs


Sophisticated ML valuation models


Definitions
Dwelling: one self-contained home (flat/house/maisonette).


Leasehold flat snag: flats don’t have a distinct plot of land, so V1 reports per-dwelling indicative land value (not per-plot land area).


Valuation model (V1)
Step-by-step
Normalize & validate postcode


Lookup postcode → borough + zone


For the zone, compute median sale price from recent transactions


Apply land-share:


land_value_per_dwelling = median_price × land_share


Apply tax rate:


annual_lvt = land_value_per_dwelling × tax_rate


Notes
Use BigDecimal for money and rates.


Rates are decimals in code (e.g., 0.40 = 40%).



Data sources (V1)
HM Land Registry Price Paid Data (sold prices)


ONS Postcode Directory (ONSPD) (postcode → geography)


V1 will filter transactions to:
Borough: Tower Hamlets


Time window: last X years (default: 5 years, configurable)



User experience (V1)
Single page:
Postcode input


Land share slider


Tax rate slider


Results cards:


Zone


Median price


Land value per dwelling


Annual LVT per dwelling


Assumptions box



API (planned)
GET /postcode/{postcode} → normalized postcode + borough + zone


GET /valuation?postcode=E14+8HX&landShare=0.4&taxRate=0.03 → valuation response


Repo structure (planned)
/engine — Pure Java valuation library (calculator + tests)


/backend — Spring Boot API (data access + endpoints)


/frontend — Web UI


/data — scripts / notes for downloading + processing datasets


Roles (suggested)
Dev (you): engine module + tests + postcode utilities + basic frontend glue


Sam: backend architecture, data ingestion pipeline, deployment

 (We’ll pair on design decisions and do PR reviews.)



Milestones
Milestone 1: Working engine (no UI)
Postcode normaliser + validator


Valuation calculator


Unit tests


Milestone 2: Working API
Data loaded (Tower Hamlets)


/postcode + /valuation endpoints return real data


Milestone 3: MVP UI
Postcode input + sliders + results on a simple page



Assumptions & limitations (V1 copy)
This tool provides an indicative estimate designed to explore scenarios.
Results are per dwelling, not per plot, because flats do not have distinct land parcels.


Typical values are derived from median recent sales in the local zone.


Outputs should not be treated as legal or financial advice.
