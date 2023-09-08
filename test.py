import pandas as pd

df = pd.read_csv('tmdb_5000_movies.csv')
movie2idx = pd.Series(df.index, index=df['title'])

# print(movie2idx.head())

titleList = df['title'].tolist()
print(len(titleList))

topDict = {}

for i in range(len(titleList)):
    firstChar = titleList[i][0].upper()
    if firstChar.isalpha() and "'" not in titleList[i]:
        if titleList[i][0] not in topDict.keys():
            topDict[titleList[i][0]] = [titleList[i]]
        elif len(topDict[titleList[i][0]]) < 6:
            topDict[titleList[i][0]].append(titleList[i])

# print(len(topDict))
# print(topDict.keys())

# print(topDict.values())

# Convert 2D list to 1D list
one_d_list = [item for sublist in topDict.values() for item in sublist]

print(one_d_list)
