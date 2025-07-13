# 🎬 Movie Recommendation System

A full-stack movie recommendation engine using **Python (Flask)** for the ML model and **Java (Spring Boot)** as a REST client.

## 💡 Project Overview

This system recommends top 5 movies to users based on collaborative filtering using **Singular Value Decomposition (SVD)** trained on the **MovieLens 100K** dataset.

| Stack          | Description                        |
|----------------|------------------------------------|
| Python         | Trains and serves ML model (Flask) |
| scikit-surprise| Matrix factorization (SVD)         |
| Java (Spring)  | Consumes recommendations via REST  |
| JUnit + Mockito| Validates integration + mock tests |

---

## 📁 Project Structure

### Python (ML & Flask API)
```
📦 movie-recommender-python
├── model/                    # Trained model files (.pkl)
├── train_model.py           # Script to train SVD model
├── app.py                   # Flask API to serve recommendations
├── requirements.txt
└── Dockerfile (optional)
```

### Java (Client Microservice)
```
📦 movie-service-java
├── src/
│   └── RecommendationService.java   # Java service that calls Flask
│   └── RecommendationResponse.java  # POJO for JSON parsing
├── test/
│   └── RecommendationServiceTest.java # Unit test with mocked Flask call
└── pom.xml
```

---

## 🚀 How It Works

1. **Python** script trains an SVD model on user-movie ratings.
2. A **Flask** API serves recommendations via `/recommend?user_id=<id>`.
3. **Java Spring Boot** service makes a POST request to get and display results.
4. Includes cold-start handling, error fallback, and input validation.

---

## ⚙️ Setup Instructions

### 🔧 Python (ML API)
```bash
git clone https://github.com/yourname/movie-recommender-python.git
cd movie-recommender-python

# Setup virtualenv and install dependencies
pip install -r requirements.txt

# Train the model
python train_model.py

# Start the API
python app.py
```

Test it:
```
http://localhost:5000/recommend?user_id=42
```

---

### 💻 Java (Client App)
```bash
git clone https://github.com/yourname/movie-service-java.git
cd movie-service-java

# Run Spring Boot
mvn spring-boot:run
```

---

## 🧪 Testing

### Python
Test `/recommend?user_id=X` using:
- Postman
- `curl`
```bash
curl http://localhost:5000/recommend?user_id=42
```

### Java (JUnit)
Tests included in `RecommendationServiceTest.java`:
- Valid ID
- Missing param
- Invalid type
- Cold-start

---

## 📦 Optional: Run with Docker
```bash
docker build -t movie-recommender .
docker run -p 5000:5000 movie-recommender
```

---

## 🧠 Model Details

- Dataset: [MovieLens 100K](https://grouplens.org/datasets/movielens/)
- Algorithm: `SVD` (Surprise)
- Input: `user_id`, `movie_id`, `rating`
- Output: Top-N movie recommendations for a given user

---

## 🔒 Cold Start Handling

If a `user_id` not present in the training set is passed, the model:
- Falls back to global/movie bias predictions
- Flags `"cold_start": true` in the response

---

## 📣 Credits

- [GroupLens](https://grouplens.org/) for the MovieLens dataset
- `scikit-surprise` for SVD implementation
- Spring Boot for Java microservice layer

---

## 🌐 Author

**Rajesh Padmanabhan**  
M.Tech in Applied AI (VNIT + Intellipaat)  
GitHub: [RjResilient94](https://github.com/RjResilient94)  
LinkedIn: [rajesh-p-87b165353](https://linkedin.com/in/rajesh-p-87b165353)

---

## 📌 License

This project is released under the MIT License.
