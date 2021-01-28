class Project {
	constructor() {
        this.projectId = undefined;
        this.name = undefined;
        this.description = undefined;
        this.email = undefined;
        this.isProtected = false;
    }

	static from(json){
		return Object.assign(new Project(), json);
	  }
}

export default Project;