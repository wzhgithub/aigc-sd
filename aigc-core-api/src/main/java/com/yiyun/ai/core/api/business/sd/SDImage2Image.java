package com.yiyun.ai.core.api.business.sd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SDImage2Image {

    @NoArgsConstructor
    @Data
    public static class SDImage2ImageRequest {

        @SerializedName("init_images")
        private List<String> initImages;
        @SerializedName("resize_mode")
        private Integer resizeMode;
        @SerializedName("denoising_strength")
        private Double denoisingStrength;
        @SerializedName("image_cfg_scale")
        private Integer imageCfgScale;
        @SerializedName("mask")
        private String mask;
        @SerializedName("mask_blur")
        private Integer maskBlur;
        @SerializedName("mask_blur_x")
        private Integer maskBlurX;
        @SerializedName("mask_blur_y")
        private Integer maskBlurY;
        @SerializedName("inpainting_fill")
        private Integer inpaintingFill;
        @SerializedName("inpaint_full_res")
        private Boolean inpaintFullRes;
        @SerializedName("inpaint_full_res_padding")
        private Integer inpaintFullResPadding;
        @SerializedName("inpainting_mask_invert")
        private Integer inpaintingMaskInvert;
        @SerializedName("initial_noise_multiplier")
        private Integer initialNoiseMultiplier;
        @SerializedName("prompt")
        private String prompt;
        @SerializedName("styles")
        private List<String> styles;
        @SerializedName("seed")
        private Integer seed;
        @SerializedName("subseed")
        private Integer subseed;
        @SerializedName("subseed_strength")
        private Integer subseedStrength;
        @SerializedName("seed_resize_from_h")
        private Integer seedResizeFromH;
        @SerializedName("seed_resize_from_w")
        private Integer seedResizeFromW;
        @SerializedName("sampler_name")
        private String samplerName;
        @SerializedName("batch_size")
        private Integer batchSize;
        @SerializedName("n_iter")
        private Integer nIter;
        @SerializedName("steps")
        private Integer steps;
        @SerializedName("cfg_scale")
        private Integer cfgScale;
        @SerializedName("width")
        private Integer width;
        @SerializedName("height")
        private Integer height;
        @SerializedName("restore_faces")
        private Boolean restoreFaces;
        @SerializedName("tiling")
        private Boolean tiling;
        @SerializedName("do_not_save_samples")
        private Boolean doNotSaveSamples;
        @SerializedName("do_not_save_grid")
        private Boolean doNotSaveGrid;
        @SerializedName("negative_prompt")
        private String negativePrompt;
        @SerializedName("eta")
        private Integer eta;
        @SerializedName("s_min_uncond")
        private Integer sMinUncond;
        @SerializedName("s_churn")
        private Integer sChurn;
        @SerializedName("s_tmax")
        private Integer sTmax;
        @SerializedName("s_tmin")
        private Integer sTmin;
        @SerializedName("s_noise")
        private Integer sNoise;
        @SerializedName("override_settings")
        private OverrideSettingsDTO overrideSettings;
        @SerializedName("override_settings_restore_afterwards")
        private Boolean overrideSettingsRestoreAfterwards;
        @SerializedName("script_args")
        private List<?> scriptArgs;
        @SerializedName("sampler_index")
        private String samplerIndex;
        @SerializedName("include_init_images")
        private Boolean includeInitImages;
        @SerializedName("script_name")
        private String scriptName;
        @SerializedName("send_images")
        private Boolean sendImages;
        @SerializedName("save_images")
        private Boolean saveImages;
        @SerializedName("alwayson_scripts")
        private AlwaysonScriptsDTO alwaysonScripts;

        @NoArgsConstructor
        @Data
        public static class OverrideSettingsDTO {
        }

        @NoArgsConstructor
        @Data
        public static class AlwaysonScriptsDTO {
        }
    }

    @NoArgsConstructor
    @Data
    public static class SDImage2ImageResponse {

        @SerializedName("images")
        private List<String> images;
        @SerializedName("parameters")
        private ParametersDTO parameters;
        @SerializedName("info")
        private String info;

        @NoArgsConstructor
        @Data
        public static class ParametersDTO {
        }
    }
}
