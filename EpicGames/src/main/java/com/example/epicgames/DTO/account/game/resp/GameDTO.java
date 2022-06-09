package com.example.epicgames.DTO.account.game.resp;

import com.example.epicgames.DTO.account.category.resp.CategoryDTO;
import com.example.epicgames.DTO.account.developer.resp.DeveloperDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameDTO {
    List<DeveloperDTO> developers;
    List<CategoryDTO> categories;
    private Integer id;
    private BigDecimal price;
    private String name;

    @Override
    public String toString() {
        return "GameDTO{" +
                "developers=" + developers +
                ", categories=" + categories +
                ", id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
