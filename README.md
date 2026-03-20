# Dynamic Flight Pricing System

A Java-based flight routing and pricing simulation that models real-world airline behaviour. The system finds routes between European airports using two different algorithms, and dynamically adjusts flight prices based on seat availability.

---

## Features

- **Two routing algorithms** — BFS for fewest stops, Dijkstra for cheapest path
- **Dynamic pricing** — prices increase automatically as seats fill up using a load factor model
- **Real-world distances** — Haversine formula calculates accurate great-circle distances between airports
- **Connection filtering** — Dijkstra enforces realistic layover windows (60–480 minutes) between connecting flights
- **Seat reservations** — booking a seat triggers an immediate price recalculation
- **Optional leg room** — +€20 per flight segment available at checkout
- **Interactive menu** — search routes by destination, compare cheapest vs fastest, view all available flights

---

## How to Compile and Run

Requires Java 17 or higher.

```bash
# Compile all files
javac *.java

# Run the program
java Main
```

The program runs a hardcoded demo first (4 sample scenarios), then offers an optional interactive menu.

---

## Project Structure

```
├── Main.java                # Entry point and demo scenarios
├── Menu.java                # Interactive user menu
├── Data.java                # Airport and flight data
├── Airport.java             # Airport model
├── Flight.java              # Flight model
├── FlightGraph.java         # Adjacency list graph structure
├── RouteFinder.java         # BFS and Dijkstra implementations
├── ReservationManager.java  # Seat reservation and price update logic
├── DistanceCalculator.java  # Haversine distance formula
├── PriceCalculator.java     # Load factor pricing model
├── RandomSeats.java         # Random seat capacity and occupancy generation
└── I*.java                  # Interfaces: IAirport, IFlight, IFlightGraph, etc.
```

---

## Algorithms

### BFS — Fewest Stops
Breadth-First Search finds the route with the minimum number of stops. It explores airports layer by layer and returns the first path that reaches the destination. It does not consider price or layover times.

### Dijkstra — Cheapest Route
Dijkstra's algorithm finds the lowest cost route using flight prices as edge weights. It uses a priority queue ordered by cumulative cost and enforces a realistic layover window of 60–480 minutes between connecting flights to prevent impossible or impractical connections.

---

## Pricing Model

Flight prices are calculated using a load factor formula:

```
price = (basePrice + distancePrice) * (1 + seatsTaken / capacity)
```

- `basePrice` — fixed starting cost of €20
- `distancePrice` — distance in km divided by 10
- `loadFactor` — multiplier between 1.0 (empty) and 2.0 (full)

As more seats are reserved, the load factor increases and the price rises automatically. Every reservation triggers a recalculation.

---

## Airport Coverage

The system includes 32 European airports across 5 regions:

| Region | Airports |
|---|---|
| Southern Europe | ATH, SKG, MLA, LCA, SOF, FCO, BCN, MAD, LIS, ZAG, IST |
| Central Europe | VIE, PRG, BUD, ZRH, BER, MUC |
| Northern Europe | CPH, ARN, OSL, HEL, RIX |
| Western Europe | LHR, MAN, EDI, CDG, BRU, AMS, DUB |
| Eastern Europe | WAW, KRK, KEF |

---

## Known Limitations

### Algorithmic
- **BFS has no time awareness** — BFS finds the fewest hops but does not check layover times. A returned route could have a 3-minute or 10-hour connection. Dijkstra correctly enforces a 60–480 minute window, but BFS does not.
- **BFS/Dijkstra asymmetry** — the two algorithms are not directly comparable. BFS optimises for stops, Dijkstra for price with time constraints. A "fastest" route from BFS is not guaranteed to be the fastest in real travel time.
- **Multiple flights per leg** — when multiple flights exist between the same two airports, route details always display the last match found in the adjacency list, not the cheapest or most time-appropriate one.
- **Dijkstra tracks airports, not flights** — the cheapest path is reconstructed using airport-to-airport parents, so the specific flight taken on each leg is re-looked up after the search rather than being tracked during it.

### Data & System
- **Non-deterministic pricing** — seat counts are randomised on every run using `RandomSeats`, so prices differ each execution and results cannot be reproduced exactly.
- **All flights on one day** — the dataset is hardcoded to a single date (`2026-02-20`). The system does not model multi-day scheduling.
- **No persistent storage** — reservations and price changes are lost when the program exits.
- **Reykjavik (KEF) is unreachable** — KEF has no outbound flights by design, so any route search involving it will fail.

