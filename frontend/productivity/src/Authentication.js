class Authentication {
	constructor() {
        this.projectId = undefined;
        this.token = undefined;
    }

	static from(json){
		return Object.assign(new Authentication(), json);
	  }
}

export default Authentication;