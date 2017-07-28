function picForward(picObjName)
{
    var index = document.all[picObjName].picIndex;
    var theImgs = null;

    try
    {
        theImgs = eval("theImgs_" + picObjName);
    }
    catch(e)
    {
        return;
    }

    if(theImgs == null || theImgs.length == 1) return;
    if(index == null)
    {
       index = 0;
    }
    else
    {
        index++; 
        if(index>theImgs.length-1) index = 0;
    }

    if(theImgs[index] != null)
    {
        document.all[picObjName].src = theImgs[index].src;
        document.all[picObjName].picIndex = index;
    }
}

function picBackward(picObjName)
{
    var index = document.all[picObjName].picIndex;
    var theImgs = null; 
   
    try
    {
        theImgs = eval("theImgs_" + picObjName);
    }
    catch(e)
    {
        return;
    }
    
    if(theImgs == null || theImgs.length == 1) return;
    if(index == null)
    {
       index = theImgs.length - 1;
    }
    else
    {
        index-- ; 
        if(index<0) index = theImgs.length-1;
    }

    if(theImgs[index] != null)
    {
        document.all[picObjName].src = theImgs[index].src;
        document.all[picObjName].picIndex = index;
    }
}