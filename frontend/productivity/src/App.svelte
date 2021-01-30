<script>
	import { onMount } from "svelte";
	import MainContent from "./MainContent.svelte";
	import ProjectNotFound from "./ProjectNotFound.svelte";

	let tasksPromise = [];
	let isProjectNotFound = false;

	const host = window.location.hostname;
	const backendUri = production() ? "https://www.gobbler.one:8443" : "https://" + host + ":8443";
	const self = production() ? "https://" + host : "https://" + host + ":5000";

	let projectPromise = [];
	let projectId;

	onMount(async () => {
		getUrlParams();
		tasksPromise = getTasks();
		projectPromise = getProject();
	});

	function production() {
		return host == "www.gobbler.one";
	}

	function getUrlParams() {
		const queryString = window.location.search;
		const urlParams = new URLSearchParams(queryString);
        
        if (urlParams.has('id')) {
            projectId = urlParams.get('id');
        }
	}

	async function getProject() {
		let result = [];
		if (projectId === undefined || projectId === "") {
			createProject();
		} else {
			result = await fetch(backendUri + "/projects/" + projectId)
				.then((response) => (result = response.json()))
				.catch(() => {
					isProjectNotFound = true;
				});
		}
		return result;
	}

	async function createProject() {
		let newProject = await fetch(backendUri + "/projects/new")
				.then((response) => (response.json()))
				.catch((error) => alert(error));
			reloadPage(newProject.projectId);
	}

	async function getTasks() {
		let result = [];
		if (!(projectId === undefined || projectId === "")) {
			result = await fetch(backendUri + "/tasks/" + projectId)
				.then((response) => response.json())
				.catch((error) => alert(error));
		}
		return result;
	}

	function saveProject(project) {
		fetch("https://" + host + ":8443/projects/", {
            method: 'POST',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json',
                'Media-Type': "MediaType.APPLICATION_JSON"
            },
            body: project
        });
	}

	function reloadPage(projectReference) {
		location.assign(self + "/?id=" + projectReference);
	}

</script>

{#if isProjectNotFound}
	<ProjectNotFound on:createProject={createProject} />
{:else}
	{#await projectPromise then project}
		{#await tasksPromise then tasks}
		<MainContent {project} {tasks} 
			on:saveProject={(event) => saveProject(event.detail.text)}
			on:undoDelete={(event) => reloadPage(event.detail.text)}
			on:saveTask={() => tasksPromise = getTasks()}
		/>
		{/await}
	{/await}
{/if}

<style>
</style>
