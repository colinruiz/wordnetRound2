Programming Assignment 6: WordNet

/* *****************************************************************************
 *  Please take a moment now to fill out the mid-semester survey:
 *  https://forms.gle/diTbj5r4o4xXbJm89
 *
 *  If you're working with a partner, please do this separately.
 *
 *  Type your initials below to confirm that you've completed the survey.
 **************************************************************************** */



/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */

I used two HashMap's to store the information in synsets.txt. The first one was
to set the synset id to the whole string of nouns. The second was to store each
noun to the synset id's it corresponded to. I decided to use two and
especially the second one to avoid for duplicate nouns in one HashMap.


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */

The data structure I used to store the information in hypernyms.txt was a Digraph.
I split the text file into two parts. One being the synset id. The second being
the hypernym id which would be the synset or synsets id/s that link to the first
id read in. Then I would just add edges into the digraph with this correspondance.

/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify
 *  your answer.
 **************************************************************************** */

Description:
I use a private helper method in my constructor that does this calculation.
The first thing I do is to see if there is only 1 root. I do this by looping
through all the vertices and checking their corresponding out degree. If its 0
then I up the rootcount by 1. In this for loop, I also put all the node's in-degree
in an array to store them for later.

Next, I do my cycle detection which I do by having nodes in at the start that have
an in-degree of 0 in a queue. These are the starting nodes.
Then I added nodes topolocigally based on which nodes are adjacent from the starting nodes
and by continuously deleting the nodes added and their corresponding edges.
While doing this, I also check if there are new nodes with in-degree of 0 and if
so, I add them into the queue. Afterwards, I check if the number of nodes in the
queue is equal to the number of nodes in the graph. If there was a cycle then not
all the nodes will be added into the graph because it will not be able to
remove the nodes outside the cycle.


Order of growth of running time:
O(E + V)


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) Use Big Theta notation to simplify your
 *  answers.
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description:
The way I did this was to run a private helper method called pathFinder which
added the source or whatever the parameter node was. After this, it used a
breadth-first search type algorithm which traveresed through all the nodes
that could be reached from the source and storing those nodes and the distances
from the source node into a hashMap. After no more nodes could be reached, it
would return the HashMap.

I did this pathFinder method for both v and w and for each of the subsets as well.
I would then compare the two HashMaps for v and w and see what nodes matched and
by using a running total, would compute the shortest common ancestor with the smallest
length or distance.

For the best case running time, it is constant because the best case comes when
both of them are the same node and both of them are the root.


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()                O(1)                  O(E+V)

ancestor()              O(1)                  O(E+V)

lengthSubset()          O(1)                  O(E+V)

ancestorSubset()        O(1)                  O(E+V)



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */



/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */

Nina(pathFinder), Professor Han(pathfinder and cycle detection.

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */



/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
N/A



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */
I enjoyed this assignment.
