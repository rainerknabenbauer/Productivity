class Authentication {
	constructor() {
        this.projectId = undefined;
        this.token = undefined;
	}
	
	toString(){
		return this.projectId + ":" + this.token;
	  }

	static from(json){
		return Object.assign(new Authentication(), json);
	  }
}

export default Authentication;