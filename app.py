
from flask import Flask, render_template, request
import joblib
import pandas as pd
import the_models as myModel

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/project')
def project():
    return render_template('project.html')

@app.route('/find', methods=['POST'])
def find():

    # transform the input text to numpy array
    form_input = request.form.to_dict()
    searchTitle = form_input['movieName']
    # print(form_input)

    resDF = myModel.recommender(searchTitle)
    ids = resDF['id'].values.tolist()
    titles = resDF['title'].values.tolist()

    links = []
    contents = []
    for i in range(7):
        contents.append(myModel.getTextPic(ids[i], titles[i]))
    return render_template('recommend.html', titles = titles, contents=contents, movieName=searchTitle)


if __name__ == '__main__':
    app.run(port=5000)
