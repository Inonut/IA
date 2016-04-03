package ia.util

/**
 * Created by Dragos on 3/25/2016.
 */
class Util {

    def static int deep = 5

    def static synchronized Integer sumDiff(int[][] mat1, int[][] mat2){
        return [mat1, mat2].transpose().collect{a,b -> [a,b].transpose().collect {q,w -> q==w?0:1 } }.flatten().sum() as int
    }
}
