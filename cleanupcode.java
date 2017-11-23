# QM Project

!mkdir projectdata
!mkdir projectdata/health


import pandas as pd
import pylab
import matplotlib.pyplot as plt

#giving data_path a route to the data
data_path = "./projectdata/health/life-expectancy.csv"

#reading the data and naming it life_expectancy
life_expectancy =  pd.read_csv(data_path)

#cleaning the data:
life_expectancy.drop('Unnamed: 0', axis=1, inplace=True)    #get rid of what I don't need, axis=1 means get rid of a horizontal (row not column) 
life_expectancy.columns = life_expectancy.iloc[0]
life_expectancy.set_index('Local Authority', inplace=True, drop=True)   #inplace = True means replace the old version with this new version, don't make 2 copies
life_expectancy.drop('Local Authority', axis=0, inplace=True)
life_expectancy.dropna(axis=0, how='all', thresh=None, subset=None, inplace=True)   #get rid of values/columns that have n/a
life_expectancy.dropna(axis=1, how='all', thresh=None, subset=None, inplace=True)

#change data from strings to numbers - not always necessary, my data just happened to be in strings
life_expectancy =life_expectancy.apply(pd.to_numeric, errors='coerce')   

#split the table into men and women
life_exp_men = life_expectancy.iloc[:, :13]     
life_exp_women = life_expectancy.iloc[:, 13:]

#new tables that just look at most recent data 
life_exp_women_recent = life_exp_women[['2012-2014']]
life_exp_men_recent = life_exp_men[['2012-2014']]

#put these two new tables together so that I'll be able to find the mean
men_and_women = pd.concat([life_exp_women_recent, life_exp_men_recent], axis=1, join='inner')
men_and_women.columns.values[0] = 'Women 2012-2014 Life Expectancy'
men_and_women.columns.values[1] = 'Men 2012-2014 Life Expectancy'
men_and_women['Mean'] = men_and_women.mean(axis=1)
men_and_women.index.names = ['London Borough']

men_and_women.head()

#I'll probably just use the mean for plotting and stuff, but I've saved the table like this so I know where the mean comes from
