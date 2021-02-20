class Credentials {
	constructor() {
        this.projectId = undefined;
        this.password = undefined;
	}
	
	toString(){
		return this.projectId + ":" + this.password;
	  }

	static from(json){
		return Object.assign(new Credentials(), json);
	  }
}

export default Credentials;