# Validation scope

The mock catalog JSON and generated WAV can be validated independently. Android assemble/unit-test checks are configured in GitHub Actions. Device UI, persistence across process restart and audible playback require an Android emulator/device run; they are not claimed verified merely because the project compiles.
