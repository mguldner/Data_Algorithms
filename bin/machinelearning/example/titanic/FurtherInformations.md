# Description of this example

This example uses the data of the competiton of Kaggle : https://www.kaggle.com/c/titanic/data
The aim is to predict if a specific passenger of the Titanic has survived or not, regarding some features (age, sex, ticket fare, etc.).

# Partitioning of the data

For features 'age' and 'ticket fare', there is too much different values. I also preferred to create Frames in order to group these values.

## First : feeling

I partitioned just to test -> no calcul/reflexion behind.
According to Kaggle, the score was about 0.77033

## Second : equal repartition of people

I tried to divide the around 800 people in 8 groups of 100 people (just did for age)
According to Kaggle, the score was about 0.77512

## Third : with clusters

I created some graphs with the data and tried to find some visual clusters (no maths behind, just looking at the graphs).
For age, 15 clusters.
For fare, 24 clusters.
According to Kaggle, the score was about 0.78469
Nevertheless, with these way of doing, I think I might be overfitting the data.
I have to find an other way to partition the data.