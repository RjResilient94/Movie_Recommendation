from flask import Flask, request, jsonify
import pickle

app = Flask(__name__)

# Load mock model
with open("model/recommender.pkl", "rb") as f:
    model = pickle.load(f)

@app.route('/predict', methods=['POST'])
def predict():
    user_id = request.json.get("user_id")
    recommendations = model.get(user_id, ["No recommendations found"])
    return jsonify({"user_id": user_id, "recommendations": recommendations})

if __name__ == "__main__":
    app.run(debug=True, port=5000)