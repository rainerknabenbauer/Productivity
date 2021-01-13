class Project {
	constructor() {
        this.projectId = undefined;
        this.name = undefined;
        this.description = undefined;
        this.email = undefined;
        this.userIds = undefined;
        this.taskIds = undefined;
    }

	static from(json){
		return Object.assign(new Project(), json);
	  }
}

export default Project;