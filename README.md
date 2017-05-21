# RubiksCubeSolver
The objective of this project is to graphically represent a Rubik's cube and to find a solution for a given state. A rubik’s cube is a three dimensional puzzle; the goal is to correctly orient all pieces such that each of the six faces only has one colour occupying it. The cube is made up of corner pieces (with 3 sides) and edge pieces (with 2 sides). In any state, a cube has 12 possible turns: the clockwise and counterclockwise rotation of the six faces. This program uses predetermined patterns and pattern recognition to piece together a solution to any state of a cube. The cube will be graphically displayed as it's geometric net and represented as a 2D array of values. This program currently generates solutions of approximately 100 quarter turns. In comparison, the optimal algorithm (Kociemba’s algorithm) can find a solution to any cube in 26 quarter turns or less.

Checkout the full <a href ="https://docs.google.com/document/d/1RCjFrMTkLgQ4N8LGd8ne0DKDvW5jo3IokHSUNM5IFTI/edit?usp=sharing">design document</a> on Google Drive.

Scramble:

<img width="654" alt="screen shot 2017-05-21 at 3 12 21 pm" src="https://cloud.githubusercontent.com/assets/7998752/26286634/08476bb8-3e38-11e7-813b-cc0035f3f73e.png">

Partially solved state:

<img width="652" alt="screen shot 2017-05-21 at 3 12 31 pm" src="https://cloud.githubusercontent.com/assets/7998752/26286635/09893ca4-3e38-11e7-8e3b-6bb1b42ad4b0.png">
