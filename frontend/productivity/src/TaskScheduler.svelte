<script>
    import Datepicker from 'svelte-calendar';
</script>

<style>
    .wrapper {
        margin: 3px;
        text-align: center;
    }
    .input {
        width: 21pt;
        height: 18pt;
        outline: none;
    }
    .info {
        color: #9c9c9c;
    }
</style>

<div class="wrapper">
    <br>This task should be completed until:
    <br><br><Datepicker format={'#{d}.#{m}.#{Y}'} />
    <br><br>Remind me in <input class="input" type="text" maxlength="2"/> days.
    <br>Remind me <input class="input" type="text" maxlength="2"/> days before deadline.
    <br><br><br><span class="info">You will receive an eMail on the day specified.</span>
</div>