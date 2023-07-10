# import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import json
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity, euclidean_distances
import urllib
from urllib.parse import urlparse
import requests
from bs4 import BeautifulSoup
import urllib.robotparser
import urllib.request
import random


def genres_keywords_to_string(row):
  genres = json.loads(row['genres'])
  genres = ' '.join(''.join(j['name'].split()) for j in genres)

  keywords = json.loads(row['keywords'])
  keywords = ' '.join(''.join(j['name'].split()) for j in keywords)
  
  return "%s %s" % (genres, keywords)


def recommender(title):
  # get the row from df for query movie
  idx = movie2idx[title]
  if type(idx) == pd.Series:
    idx = idx.iloc[0]

  # cal the pairwise similarities for query
  query = X[idx]
  scores = cosine_similarity(query, X)

  # flatten the array
  scores = scores.flatten()

  # retrieve the top 6 recommended movies
  recommended_idx = (-scores).argsort()[0:7]

  return df[['id', 'title']].iloc[recommended_idx]


def conTitle(title):
  title = title.lower()
  title = title.replace(":", "")
  title = title.replace(" ", "-")
  return title


def trim_sentence(sentence, length):
    if len(sentence) <= length:
        return sentence
    else:
        return sentence[:length].rsplit(' ', 1)[0] + ' ...'


def getTextPic(id, title):

  headers = [
                {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/110.0'},
                {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.115 Safari/537.36'},
                {'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/110.0'},
                {'User-Agent': 'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36'}
            ]

  link = link = "https://www.themoviedb.org/movie/" + str(id) + "-" + conTitle(title)
  res = requests.get(url=link, headers=random.choice(headers), timeout=5)
  soup = BeautifulSoup(res.text, "html.parser")
  # Find the specific element using its tag, class, id, or other attributes
  element = soup.find('div', class_='overview')
  
  # Extract the desired information from the element
  if element:
      overView = element.text.strip()
      overView = trim_sentence(overView, 290)
      # print(element)
  else:
      overView = 'Content NOT found!'

  image_elements = soup.find_all('img')
  # Print the image URLs
  posterUrl = ''
  for img in image_elements:
    class_name = img.get('class')
    if class_name and 'poster' in class_name and 'lazyload' in class_name:
      posterUrl = "https://www.themoviedb.org" + img['data-src']

  return [overView, posterUrl]


def getIdByTitle(title):
  the_id = df.loc[df["title"] == title, "id"]

  return the_id


df = pd.read_csv('tmdb_5000_movies.csv')
movie2idx = pd.Series(df.index, index=df['title'])
tfidf = TfidfVectorizer(max_features = 2000)
# create a new string representation of each movie
df['string'] = df.apply(genres_keywords_to_string, axis=1)
X = tfidf.fit_transform(df['string'])


if __name__=='__main__':
  resDF = recommender('The Avengers')
  ids = resDF['id'].values.tolist()
  titles = resDF['title'].values.tolist()

  # links=[]
  # for i in range(6):
  #   link = "https://www.themoviedb.org/movie/" + str(ids[i]) + "-" + conTitle(titles[i])
  #   links.append(link)
  #   print(titles[i])
  #   print(getTextPic(link))
