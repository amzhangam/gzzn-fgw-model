package com.gzzn.fgw.webUtil;

import java.io.Serializable;
import com.gzzn.fgw.model.Pjadjunct;


public class FjObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3968411573852979815L;

	private Long fjId;
	
    private Pjadjunct pjadjunct;
	
	public FjObject(){
		
	}
	
	public FjObject(Long fjId,Pjadjunct pjadjunct){
		
		this.fjId = fjId;
		
		this.pjadjunct = pjadjunct;
		
	}

	public Long getFjId() {
		return fjId;
	}

	public void setFjId(Long fjId) {
		this.fjId = fjId;
	}

	public Pjadjunct getPjadjunct() {
		return pjadjunct;
	}

	public void setPjadjunct(Pjadjunct pjadjunct) {
		this.pjadjunct = pjadjunct;
	}
	
	
}
