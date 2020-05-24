<#macro url_and_image>
<div class="input-group mb-2">
    <div class="col-2">
        <div class="input-group-prepend">
            <label for="url_input" class="mr-3 col-form-label">URL</label>
        </div>
    </div>
    <div class="col-10">
        <input id="url_input" type="text" class="req form-control" placeholder="http://..." aria-label="url" aria-describedby="url_input"
               name="url">
    </div>
</div>

<div class="input-group mb-2">
    <div class="col-2">
        <label for="url_input" class="mr-3 col-form-label">Image</label>
    </div>
    <div class="col-10">
        <div class="input-group-prepend">
            <input type="file" class="custom-file-input" id="image_input" aria-describedby="image" name="image">
            <label class="custom-file-label input-group-prepend form-control" for="image_input">Add image</label>
        </div>
    </div>
</div>
</#macro>