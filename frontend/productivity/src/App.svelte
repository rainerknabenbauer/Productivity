<script>
	import { onMount } from "svelte";
	import Authentication from "./Authentication.svelte";
	import MainContent from "./MainContent.svelte";
	import ProjectNotFound from "./ProjectNotFound.svelte";

	let tasksPromise = [];
	let isProjectNotFound = false;
	let unauthenticated = true;

	const host = window.location.hostname;
	const backendUri = production() ? "https://productivity.to:8443" : "https://" + host + ":8443";
	const self = production() ? "https://" + host : "http://" + host + ":5000";

	let projectPromise = [];
	let projectId;

	onMount(async () => {
		getUrlParams();
		projectPromise = await getProject();
		tasksPromise = projectPromise.isProtected ? [] : getTasks()
	});

	function production() {
		return host == "productivity.to";
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

	async function authenticated() {
		tasksPromise = getTasks();
		unauthenticated = false;
	}

	async function getTasks() {
		getUrlParams();
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

	function logout() {
		document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC;"
		reloadPage(projectId)
	}

</script>

{#if isProjectNotFound}
	<ProjectNotFound on:createProject={createProject} />
{:else}
	{#await projectPromise then project}
		{#if project.isProtected && unauthenticated}
			<Authentication {project} {host} on:authenticated={authenticated}/>
		{:else}
			{#await tasksPromise then tasks}
				<MainContent
					{project}
					{tasks}
					on:saveProject={(event) => saveProject(event.detail.text)}
					on:undoDelete={(event) => reloadPage(event.detail.text)}
					on:saveTask={() => tasksPromise = getTasks()}
					on:logout={logout}
				/>
			{/await}
		{/if}
	{/await}
{/if}

<style>
</style>
