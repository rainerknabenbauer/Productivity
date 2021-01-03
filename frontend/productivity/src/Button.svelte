<script>
    
    async function getMock() {
		const res = await fetch('http://localhost:8080');
        return await res.text();
    }

    let text = getMock();

    function handleClick() {
        text = getMock();
    }
    
</script>

<style>

</style>

<button on:mouseup={() => handleClick}>
    {#await text}
    Nothing
    {:then mock}
    {mock}
    {/await}
</button>