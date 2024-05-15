<#list films as film>
    <div class="col-md-4 d-flex justify-content-center mb-4">
        <a href="/films/${film.id}" class="text-decoration-none">
            <div class="card card-custom shadow-sm">
                <img src="/images/${film.picture}" class="card-img-top card-img-top-custom" alt="${film.name}">
                <div class="card-body card-body-custom">
                    <h5 class="card-title">${film.name}</h5>
                </div>
            </div>
        </a>
    </div>
</#list>

<!-- Paginación -->
<nav aria-label="Page navigation" id="paginationNav">
    <ul class="pagination justify-content-center">
        <li class="page-item<#if currentPage == 0> disabled</#if>">
            <a class="page-link pagination-link" href="#" data-page="${currentPage - 1}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <#list 0..totalPages-1 as page>
            <li class="page-item<#if currentPage == page> active</#if>">
                <a class="page-link pagination-link" href="#" data-page="${page}">${page + 1}</a>
            </li>
        </#list>
        <li class="page-item<#if currentPage == totalPages - 1> disabled</#if>">
            <a class="page-link pagination-link" href="#" data-page="${currentPage + 1}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>

