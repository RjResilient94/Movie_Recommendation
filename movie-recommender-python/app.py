from flask import Flask, request, jsonify
import pickle
import os
import logging

app = Flask(__name__)

# --- Logging Setup ---
logging.basicConfig(level=logging.INFO)

# --- Load Model and Data ---
try:
    with open("model/recommender_model.pkl", "rb") as f:
        model = pickle.load(f)

    with open("model/movie_map.pkl", "rb") as f:
        movie_map = pickle.load(f)

    movie_ids = list(movie_map.keys())
except Exception as e:
    app.logger.error(f"❌ Failed to load model or data: {e}")
    raise RuntimeError("Startup error: model/data not loaded")

# --- Recommendation Endpoint ---
@app.route('/recommend', methods=['GET'])
def recommend():
    user_id = request.args.get('user_id')

    # Validate presence
    if not user_id:
        return jsonify({"error": "Missing user_id parameter"}), 400

    # Validate type
    try:
        user_id = int(user_id)
    except ValueError:
        return jsonify({"error": "user_id must be an integer"}), 400

    # Cold start handling
    is_known_user = user_id in model.trainset._raw2inner_id_users
    if not is_known_user:
        app.logger.warning(f"⚠️ Cold-start: user {user_id} not in training set")

    # Predict for all movies
    predictions = []
    for movie_id in movie_ids:
        pred = model.predict(user_id, movie_id)
        predictions.append((movie_id, pred.est))

    top_movies = sorted(predictions, key=lambda x: x[1], reverse=True)[:5]
    recommended_titles = [movie_map[mid] for mid, _ in top_movies]

    return jsonify({
        "user_id": user_id,
        "cold_start": not is_known_user,
        "recommendations": recommended_titles
    })

# --- Health Check ---
@app.route('/')
def index():
    return "🎬 Movie Recommendation API is running!"

if __name__ == '__main__':
    app.run(debug=False, host="0.0.0.0", port=5000)
