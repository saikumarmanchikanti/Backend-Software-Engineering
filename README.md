# Fetch Rewards Backend Software Engineering

The coding exercise includes 
1. The ability for Fetch Rewards participating companies to reward points to consumers.
2. The ability for consumers to spend points awarded to them.

### Prerequisites

- Java 11 - for installation instructions see https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

### Usage

#### Windows

In the root directory of the project run...
```
gradlew.bat bootRun
```

#### Ubuntu / macOS

```
./gradlew bootRun
```

### REST API

REWARD POINTS

`POST /payer/transaction`

```
curl -d  '{ "payer": "DANNON", "points": 1000, "timestamp": "2020-11-02T14:00:00Z" }' -H 'Content-Type: application/json' localhost:8080/payer/transaction
```

SPEND POINTS

`POST /consumer/points`

```
curl -d  '{ "points": 5000 }' -H 'Content-Type: application/json' localhost:8080/consumer/points
```

GET POINT BALANCES REWARDED BY FETCH PARTICIPANTS

`GET /payer/balances`

```
curl -d  localhost:8080/payer/balances
```

### "Roadmap" 

- Additional tests for edge cases as well as a larger coverage at the unit layer. 
- Swagger documentation as well as thorough logging
- Consider what to return if a consumer spends more points than are rewarded to them
- Add authentication as well as rate limiting
- Monitoring
