package isel.sisinf.jpa.repo;

import isel.sisinf.model.Shop;
import java.util.Collection;

public interface IShopRepository extends IRepository<Shop, Collection<Shop>, Long> , IShopDataMapper
{

}