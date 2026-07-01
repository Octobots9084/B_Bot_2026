public class FilteredCameraResults {
    /*
    type: singletag, multitag, notargets
    photonTrackedTargets: all the information about one target/tag
    EstimatedRobotPose: the pose of the robot
    PhotonPipelineResult: all the information from one reading
    */

    //enum type;
    //List<photonTrackedTargets> targets = new ArrayList();
    //EstimatedRobotPose visionEst;
    //PhotonPipelineResult result;

    //constructor takes in a raw result
    //public FilteredResults(result){
        //result = this.result;

        //the list of targets from the result gets filled
        //for(int i = 0; i< result.getTargets().size(); i++){
            //targets.add((result.getTargets()).get(i));
        //}        

        //if there are no targets this result has no targets
        //if(targets.size() == 0){
            //type = notargets;

        //only happens if there are targets but you can't calculate a multitag result
        //}else if(photonEstimator.estimateCoprocMultiTagPose(result).isNull()){
            //type = singletag;
        //}

        //otherwise multitag
        //else{
            //type = multitag;
        //}

        //calculate pose
        //if(type == multitag){
            //visionEst = photonEstimator.estimateCoprocMultiTagPose(result);
        //}

        //if(type == singleTag){
            //visionEst = photonEstimator.estimateLowestAmbiguityPose(result);
        //}
    //}

}
