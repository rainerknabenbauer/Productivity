class Session {
	constructor() {
        this.projectId = undefined;
        this.token = undefined;
	}
	
	toString(){
		return this.projectId + ":" + this.token;
	  }

	static from(json){
		return Object.assign(new Session(), json);
	  }
}

export default Session;