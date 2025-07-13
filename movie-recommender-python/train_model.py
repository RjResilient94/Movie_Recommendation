

from surprise import Dataset, Reader, SVD
from surprise.model_selection import train_test_split
import pandas as pd
import pickle
import os


ratings = pd.read_csv("https://files.grouplens.org/datasets/movielens/ml-100k/u.data", sep="\t", names=["user_id","movie_id","rating","timestamp"])
movies = pd.read_csv("https://files.grouplens.org/datasets/movielens/ml-100k/u.item",sep='|', encoding='latin-1', header=None, usecols=[0, 1])
movies.columns = ["movie_id", "title"]

reader = Reader(rating_scale=(1,5))
data = Dataset.load_from_df(ratings[["user_id", "movie_id", "rating"]], reader)

trainset, _ = train_test_split(data, test_size=0.2)

os.makedirs("model", exist_ok=True)
model = SVD()
model.fit(trainset)
movie_map = dict(zip(movies.movie_id, movies.title))


with open("model/recommender_model.pkl", "wb") as f:
    pickle.dump(model, f)

with open("model/movie_map.pkl", "wb") as f:
    pickle.dump(movie_map, f)

print("✅ Model trained and saved in /model/")