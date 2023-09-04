<#ftl output_format="JSON">
{
  "alwayson_scripts": {
    "controlnet": {
      "args": [
        {
          "enabled": true,
          "control_mode": 0,
          "guidance_end": 0.8,
          "guidance_start": 0.0,
          "input_image": "${base64SrcImg}",
          "model": "control_v1p_sd15_qrcode_monster_v1 [a6e58995]",
          "pixel_perfect": false,
          "resize_mode": 1,
          "weight": 1.5
        },
        {
          "enabled": true,
          "control_mode": 0,
          "guidance_end": 0.78,
          "guidance_start": 0.47,
          "input_image": "${base64SrcImg}",
          "model": "control_v1p_sd15_brightness [5f6aa6ed]",
          "pixel_perfect": false,
          "resize_mode": 1,
          "weight": 0.3
        }
      ]
    }
  },
  "override_settings": {
      "sd_model_checkpoint": "darkSushiMixMix_225D.safetensors [cca17b08da]"
  },
  "batch_size": 1,
  "cfg_scale": 7,
  "negative_prompt": "(worst quality:2),(low quality:2),(bad_prompt_version2:0.8),(easynegative:1.2),badhandv4,text,name,letters,watermark,NSFW,(normal quality:2),lowres,monochrome,greyscale,skin spots,acnes,skin blemishes,character,tags,age spot,(ugly:1.331),(duplicate:1.331),",
  "clip_skip": 2,
  "prompt": "masterpiece,best quality,highlydetailed,ultra-detailed,emtremely detailed CG uinty 8k wallpaper,novel illustration,len 135mm,big Chinese white tea,strong,fragrant,dense,",
  "restore_faces": false,
  "sampler_index": "DPM++ 2S a Karras",
  "seed": 2518109800,
  "steps": 30,
  "tiling": false,
  "width": 768,
  "height": 768
}