# -*- coding: utf-8 -*-
"""YuuvisHackathon

Automatically generated by Colaboratory.

Original file is located at
    https://colab.research.google.com/drive/1S1-Cn1E4ErlXwjdYefeKGfx5mHUDgWKm
"""

import spacy
import pandas as pd
import re
import matplotlib.pyplot as plt
import matplotlib.dates as dates
# %matplotlib inline

from keras.applications.resnet50 import ResNet50
from keras.preprocessing import image
from keras.applications.resnet50 import preprocess_input, decode_predictions
import numpy as np
import datetime

nlp = spacy.load('en')

#Used in consumer() and business() functions to extract hashtags from descriptions.
def str2hash(s):
  return(" ".join(re.findall(r"#(\w+)", s)))


#Runs a pretrained ImageNet model on each image to produce addl. word labels for posts.
def imtohash(img_path):
  model = ResNet50(weights='imagenet')
  img = image.load_img(img_path, target_size=(224, 224))
  x = image.img_to_array(img)
  x = np.expand_dims(x, axis=0)
  x = preprocess_input(x)
  preds = model.predict(x)
  return(decode_predictions(preds, top=3)[0])


#Appends a column with addl. word labels for posts from ImageNet by looping imtohash() for each image.
def appendims(df):
  list_of_image_paths = df["image"].tolist()
  imlist=[]
  for path in list_of_image_paths:
    x=imtohash(path)
    x=x[0][1].replace("_"," ")+" "+x[1][1].replace("_"," ")+" "+x[2][1].replace("_"," ")
    imlist.append(x)
  df["image"] = imlist
  return

#SINGLE .PY FILE VERSION OF ABOVE CODE
def main(csv,query,cutoff):
  
  query=nlp(query)
  data = pd.read_csv(csv)
  appendims(data)
  data["hashtag"] = data["description"].apply(lambda s: str2hash(str(s)))
  data["description"] = data["description"].apply(lambda s: (str(s)))
  data["sort"] = data["hashtag"].apply(lambda s: nlp(s).similarity(query)) + data["description"].apply(lambda s: nlp(s).similarity(query)) + data["image"].apply(lambda s: nlp(s).similarity(query))
  data.sort_values(by='sort', ascending=False)
  data=data.iloc[:cutoff]
  data=data.sort_values(by='sort', ascending=False)
  
  locdata=data["location"].tolist()
  userdata=data["user"].tolist()
  
  with open('locations.txt', 'w') as filehandle:
    for listitem in locdata:
        filehandle.write('%s\n' % listitem)
  with open('usernames.txt', 'w') as filehandle:
    for listitem in userdata:
        filehandle.write('%s\n' % listitem)
        
  time = dates.date2num(list_of_datetimes)
  lfratio = data["sort"].tolist()
  time = data["time"].tolist()
  dates=[]
  for date in time:
  y = int(date[0:3])
  m = int(date[5:6])
  d = int(date[8:9])
  dates.append((y*365)+(m*30)+d)

  plt.plot_date(dates, lfratio)
  plt.savefig("graph.png")
  
  return

with open('/content/blah.txt', 'r') as filehandle:
  query = filehandle.readline()
  cutoff = int(filehandle.readline())

main('~/input.csv', query, cutoff)