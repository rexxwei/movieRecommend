Movie Recommend System
=========
Based on the Kaggle IDMB Movie dataset, the project train a model to learn the consine distance among different movies according to the description, title and audience rating.
When user input a movie name, the application will find the closest top 6 movie and return info about them.<br>

Link of live demo page can be found at: <br>
https://rexxwei.github.io/portfolio/


Dependence
----
To make this project work, below libraries or features must included in your Python environment. 
  - Flask
  - SciKit-Learn
  - Pandas
  
The dependence can be installed by execute below command
```css
pip install -r requirements.txt
```


How to Use
----
In the project directory, run the Python file 'app.py'.<br>
```css
python app.py
```
Then open a browser and try the address like:<br>
```css
localhost:5000
```

Demo
=========
![demo](demo.gif "Demo")
