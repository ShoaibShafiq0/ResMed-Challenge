# ResMed-Challenge

# Step 1
The application requires MySQL database to be setup with the following credentials, please change these with and point to the mysql database as 
required in application.properties file.

Database Name: users
DB User: root
DB Password: password

# Step 2
Before the application to work, a user needs to be created in the database. You can do that with a simple POST request like so 
##URL: http://localhost:8080/api/users/register
{
    "username": "test",
    "password": "password"
}

# Step 3
Once the registration is done, continue with the same JSON payload to login 
##URL : http://localhost:8080/api/users/login

#Step 4
By default a user can play with a computer only, and each api call to play a round needs to have username included in it for user verification.
##URL : http://localhost:8080/api/play

{
    "username": "test",
    "hand": "ROCK"
}

// Acceptable values for "hand" are ROCK, PAPER, SCISSOR


The logic works by making use of the following table rule

			ROCK    | PAPER	  | SCISSOR
ROCK		DRAW    | PAPER   | ROCK
PAPER		PAPER   | DRAW	  | SCISSOR
SCISSOR		ROCK    | SCISSOR | DRAW