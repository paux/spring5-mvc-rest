package cc.paukner.controllers.v1;

import cc.paukner.api.v1.model.VendorDto;
import cc.paukner.api.v1.model.VendorListDto;
import cc.paukner.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static cc.paukner.controllers.v1.VendorController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VendorController.class)
public class VendorControllerTest extends AbstractRestControllerTest {

    public static final String NAME = "ACME";
    public static final String VENDOR_API = BASE_URL + 1;

    @MockBean // will be provided by Spring context
    VendorService vendorService;

//    @InjectMocks
//    VendorController vendorController;

    @Autowired // will be provided by Spring context
    MockMvc mockMvc;

    @Before
    public void setUp() {
        // no need anymore
//        MockitoAnnotations.initMocks(this);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
//                .setControllerAdvice(new RestResponseEntityExceptionHandler())
//                .build();
    }

    @Test
    public void listAllVendors() throws Exception {
        VendorDto vendorDto1 = VendorDto.builder()
//                .id(1L)
                .name(NAME)
                .build();
        VendorDto vendorDto2 = VendorDto.builder()
//                .id(2L)
                .name("GmbH AG")
                .build();

        given(vendorService.getAllVendors()).willReturn(new VendorListDto(List.of(vendorDto1, vendorDto2)));

        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorByName() throws Exception {
        VendorDto vendorDto = VendorDto.builder()
//                .id(1L)
                .name(NAME)
                .build();

        given(vendorService.getVendorByName(anyString())).willReturn(vendorDto);

        mockMvc.perform(get(BASE_URL + "name=" + NAME)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void createVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder()
//                .id(1L)
                .name(NAME)
                .build();
        VendorDto returnDto = VendorDto.builder()
                .name(vendorDto.getName())
                .vendorUrl(VENDOR_API)
                .build();

        given(vendorService.createVendor(vendorDto)).willReturn(returnDto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_API)));
    }

    @Test
    public void updateVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder()
//                .id(1L)
                .name(NAME)
                .build();
        VendorDto returnDto = VendorDto.builder()
                .name(vendorDto.getName())
                .vendorUrl(VENDOR_API)
                .build();

        given(vendorService.updateVendor(anyLong(), any(VendorDto.class))).willReturn(returnDto);

        mockMvc.perform(put(VENDOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_API)));
    }

    @Test
    public void patchVendor() throws Exception {
        VendorDto vendorDto = VendorDto.builder().name(NAME).build();
        VendorDto returnDto = VendorDto.builder()
                .name(vendorDto.getName())
                .vendorUrl(VENDOR_API)
                .build();

        given(vendorService.patchVendor(anyLong(), any(VendorDto.class))).willReturn(returnDto);

        mockMvc.perform(patch(VENDOR_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_API)));
    }

    @Test
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(VENDOR_API)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }
}
